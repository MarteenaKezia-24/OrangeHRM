package com.disney.commons;

/**
 * A struct for a Sequoia element specifier.
 * <p>
 * Does not include the tree root.
 * <p>
 * Simply used to bundle together the different parts of an element specifier in
 * a single object.
 */
public class SequoiaElementKey {
	public final String pageName;
	public final String elementType;
	public final String elementName;

	public SequoiaElementKey(String pageName, String elementType, String elementName) {
		this.pageName = pageName;
		this.elementType = elementType;
		this.elementName = elementName;
	}

	/**
	 * Convert Sequoia Element key to string.
	 * <p>
	 * Does not include tree root.
	 *
	 * @return The Sequoia element key string.
	 */
	@Override
	public String toString() {
		return String.format("%s.%s.%s", pageName, elementType, elementName);
	}
}
