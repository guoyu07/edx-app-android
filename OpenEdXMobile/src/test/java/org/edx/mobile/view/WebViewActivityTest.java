package org.edx.mobile.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import org.edx.mobile.R;
import org.edx.mobile.test.BaseTestCase;
import org.edx.mobile.view.dialog.WebViewActivity;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowWebView;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class WebViewActivityTest extends BaseTestCase {

    /**
     * Testing method for displaying web view dialog
     */
    @Test
    public void test_StartWebViewActivity_LoadsUrlAndShowsTitle() {
        final String url = "https://www.edx.org";
        final String title = "title";
        test_StartWebViewActivity_LoadsUrlAndShowsTitle(url, title);
        test_StartWebViewActivity_LoadsUrlAndShowsTitle(url, null);
    }

    /**
     * Generic method for testing proper display of WebViewDialogFragment
     *
     * @param url   The url to load
     * @param title The title to show, if any
     */
    protected static void test_StartWebViewActivity_LoadsUrlAndShowsTitle(@NonNull String url,
                                                                          @Nullable String title) {
        final WebViewActivity activity =
                Robolectric.buildActivity(WebViewActivity.class)
                        .withIntent(WebViewActivity.newIntent(
                                RuntimeEnvironment.application, url,title)).setup().get();
        final View contentView = Shadows.shadowOf(activity).getContentView();
        assertNotNull(contentView);
        final WebView webView = (WebView) contentView.findViewById(R.id.webView);
        assertNotNull(webView);
        final ShadowWebView shadowWebView = Shadows.shadowOf(webView);
        assertEquals(shadowWebView.getLastLoadedUrl(), url);
        final ActionBar actionBar = activity.getSupportActionBar();
        assertNotNull(actionBar);
        assertTrue(actionBar.isShowing());
        if (!TextUtils.isEmpty(title)) {
            assertTrue(title.equals(actionBar.getTitle()));
        }
        activity.onBackPressed();
        assertTrue(activity.isFinishing());
    }
}
