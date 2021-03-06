package com.codebaum.livingsocialchallenge.network;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Use this class to enable app-level caching of network responses for JSONArray requests.
 *
 * The overriding of the caching policy is useful when the server doesn't respond with valid cache limits, or if more control over caching is needed.
 *
 * Created by brandon on 11/26/14.
 */
public class CustomJsonArrayRequest extends JsonArrayRequest
{
    public CustomJsonArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener)
    {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString), parseIgnoreCacheHeaders(response));
        } catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        } catch (JSONException je)
        {
            return Response.error(new ParseError(je));
        }
    }

    /**
     * Ignores the cache headers the server responds with and instead uses custom values.
     * @param response
     * @return
     */
    public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
        final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }
}
