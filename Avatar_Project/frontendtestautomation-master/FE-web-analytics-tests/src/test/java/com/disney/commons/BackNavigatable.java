package com.disney.commons;

/**
 * This functional interface exists as a contract between the web and android versions of sequoia specifically to
 * allow the navigate back method.
 * Created by Trammel on 1/5/17.
 */
public interface BackNavigatable
{
    /**
     * <h>Public Method - Navigate Back</h>
     * <p>This method will navigate back. On web this means it will navigate back to the previous page. On Android this means it will press the back button.</p>
     */
    void navigateBack();
}
