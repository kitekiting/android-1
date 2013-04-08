/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.plugin.thunderbird;

import java.util.regex.*;

import org.jitsi.service.configuration.*;

import net.java.sip.communicator.service.contactsource.*;

/**
 * Query creator for an instance of a Thunderbird address book.
 * 
 * @author Ingo Bauersachs
 */
public class ThunderbirdContactSourceService
    implements ExtendedContactSourceService
{
    /**
     * Name of the base-property for a Thunderbird address book configuration.
     */
    public final static String PNAME_BASE_THUNDERBIRD_CONFIG =
        "net.java.sip.communicator.plugin.thunderbird.source";

    /**
     * Property-name, appended to
     * {@link ThunderbirdContactSourceService#PNAME_BASE_THUNDERBIRD_CONFIG} ,
     * that indicates the address-book database filename.
     */
    public final static String PNAME_FILENAME = "FILENAME";

    /**
     * Property-name, appended to
     * {@link ThunderbirdContactSourceService#PNAME_BASE_THUNDERBIRD_CONFIG} ,
     * that indicates the display-name shown as the contact group.
     */
    public final static String PNAME_DISPLAYNAME = "DISPLAYNAME";

    /**
     * Property-name, appended to
     * {@link ThunderbirdContactSourceService#PNAME_BASE_THUNDERBIRD_CONFIG} ,
     * that indicates the priority in the search results.
     */
    public final static String PNAME_INDEX = "INDEX";

    /**
     * Property-name, appended to
     * {@link ThunderbirdContactSourceService#PNAME_BASE_THUNDERBIRD_CONFIG} ,
     * that indicates the telephone-number prefix.
     */
    public final static String PNAME_PREFIX = "PREFIX";

    /** Reference to the configuration service */
    ConfigurationService config = ThunderbirdActivator.getConfigService();

    /** The base property name to which the other PNAME_ will be appended */
    private final String baseConfigProperty;

    /** Value of property {@link #PNAME_FILENAME} */
    private String fileName;

    /** Value of property {@link #PNAME_DISPLAYNAME} */
    private final String displayName;

    /** Value of property {@link #PNAME_INDEX} */
    private final int index;

    /** Value of property {@link #PNAME_PREFIX} */
    private String prefix;

    /**
     * Creates a new instance of this class.
     * 
     * @param baseConfigProperty The base property name of the config for this
     *            instance
     */
    public ThunderbirdContactSourceService(String baseConfigProperty)
    {
        this.baseConfigProperty = baseConfigProperty;
        this.fileName =
            config.getString(baseConfigProperty + "." + PNAME_FILENAME);
        this.displayName =
            config.getString(baseConfigProperty + "." + PNAME_DISPLAYNAME);
        this.index = config.getInt(baseConfigProperty + "." + PNAME_INDEX, 0);
        this.prefix = config.getString(baseConfigProperty + "." + PNAME_PREFIX);
    }

    /**
     * Gets the base property name to which the other PNAME_ will be appended.
     * @return the base property name.
     */
    String getBaseConfigProperty()
    {
        return this.baseConfigProperty;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.java.sip.communicator.service.contactsource
     * .ContactSourceService#getType()
     */
    public int getType()
    {
        return DEFAULT_TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.java.sip.communicator.service.contactsource
     * .ContactSourceService#getDisplayName()
     */
    public String getDisplayName()
    {
        return this.displayName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.java.sip.communicator.service.contactsource
     * .ContactSourceService#queryContactSource(java.lang.String)
     */
    public ContactQuery queryContactSource(String queryString)
    {
        return new ThunderbirdContactQuery(this, Pattern.compile(queryString,
            Pattern.CASE_INSENSITIVE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.java.sip.communicator.service.contactsource.ContactSourceService#
     * queryContactSource(java.lang.String, int)
     */
    public ContactQuery queryContactSource(String queryString, int contactCount)
    {
        return new ThunderbirdContactQuery(this, Pattern.compile(queryString,
            Pattern.CASE_INSENSITIVE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.java.sip.communicator.service.contactsource.ExtendedContactSourceService
     * #queryContactSource(java.util.regex.Pattern)
     */
    public ContactQuery queryContactSource(Pattern queryPattern)
    {
        return new ThunderbirdContactQuery(this, queryPattern);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.java.sip.communicator.service.contactsource.ContactSourceService#
     * getIndex()
     */
    public int getIndex()
    {
        return this.index;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.java.sip.communicator.service.contactsource.ExtendedContactSourceService
     * #getPhoneNumberPrefix()
     */
    public String getPhoneNumberPrefix()
    {
        return this.prefix;
    }

    /**
     * Sets a new phone number prefix to use from now on.
     * @param prefix the new prefix.
     */
    void setPhoneNumberPrefix(String prefix)
    {
        this.prefix = prefix;
        config.setProperty(this.baseConfigProperty + "." + PNAME_PREFIX,
            prefix);
    }

    /**
     * Gets the filename to the address book database processed by this
     * ContactSource.
     * 
     * @return The filename to the address book database.
     */
    String getFilename()
    {
        return this.fileName;
    }

    /**
     * Sets a new database file name to use from now on.
     * @param filename the new file name.
     */
    void setFilename(String filename)
    {
        this.fileName = filename;
        config.setProperty(this.baseConfigProperty + "." + PNAME_FILENAME,
            filename);
    }
}
