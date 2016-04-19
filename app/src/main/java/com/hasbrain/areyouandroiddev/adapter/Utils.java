package com.hasbrain.areyouandroiddev.adapter;

import android.content.res.Configuration;
import android.text.Html;
import android.text.Spanned;

import com.hasbrain.areyouandroiddev.PostListActivity;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.Calendar;

/**
 * Created by Levin on 17/04/2016.
 */
public class Utils {
    private static final String STICKYCOLOR = "387801", BACKCOLOR = "000000", WHITECOLOR = "FFFFFF";
    private static final String AUTHOR_SUBREDDIT = "<font color='#0A295A'><b>%s</b></font> in <font color='#0A295A'><b>%s</b></font>";
    private static final String TITLE_FORMAT = "<font color='#%s'>%s</font>";
    private static final String COMMENT_DOMAIN_CREATEDTIME = "%d Comments • %s • %s ago";

    public static Spanned buildTextViewFirst(String author, String subreddit, int screenMode) {
        if (screenMode == Configuration.ORIENTATION_PORTRAIT) {
            return Html.fromHtml(String.format(AUTHOR_SUBREDDIT, author, subreddit));
        } else
            return Html.fromHtml(String.format(author));
    }

    public static Spanned buildTextViewSecond(String title, boolean isSticky, int screenMode) {
        return Html.fromHtml(String.format(TITLE_FORMAT,
                (isSticky ? (STICKYCOLOR) : (screenMode == Configuration.ORIENTATION_PORTRAIT ? BACKCOLOR : WHITECOLOR)),title));
    }

    public static String buildTextViewThird(int comment, String domain, long UTC) {
        return String.format(COMMENT_DOMAIN_CREATEDTIME, comment, domain, getStringCreateUTC(UTC));
    }



    public static String getStringCreateUTC(long createUTC) {
        long current = Calendar.getInstance().getTimeInMillis();
        long time = current - (createUTC * 1000);
        int[] times = {1000, 60, 60, 24, 30, 12};
        String strTime = "";
        int i;
        for (i = 0; i < times.length - 1; i++) {
            time /= times[i];
            if (time < times[i + 1])
                break;
        }
        switch (i) {
            case 1:
                strTime = "seconds";
                break;
            case 2:
                strTime = "minutes";
                break;
            case 3:
                strTime = "days";
                break;
            case 4:
                strTime = "months";
                break;
            case 5:
                strTime = "year";
                break;
        }
        return time + " " + strTime.toString();
    }


}
