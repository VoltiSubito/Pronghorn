package com.ociweb.pronghorn.network.twitter;

import com.ociweb.pronghorn.util.TrieKeyable;

public enum TwitterKey implements TrieKeyable<TwitterKey> {

    ASPECT_RATIO("aspect_ratio"),
    ATTRIBUTES("attributes"),
    BITRATE("bitrate"),
    BOUNDING_BOX("bounding_box"),
    CODE("code"),
    CONTENT_TYPE("content_type"),
    CONTRIBUTORS("contributors"),
    CONTRIBUTORS_ENABLED("contributors_enabled"),
    COORDINATES("coordinates"),
    COUNTRY("country"),
    COUNTRY_CODE("country_code"),
    CREATED_AT("created_at"),
    DEFAULT_PROFILE("default_profile"),
    DEFAULT_PROFILE_IMAGE("default_profile_image"),
    DELETE("delete"),
    DESCRIPTION("description"),
    DISPLAY_TEXT_RANGE("display_text_range"),
    DISPLAY_URL("display_url"),
    DURATION_MILLIS("duration_millis"),
    ENTITIES("entities"),
    EXPANDED_URL("expanded_url"),
    EXTENDED_ENTITIES("extended_entities"),
    EXTENDED_TWEET("extended_tweet"),
    FAVORITE_COUNT("favorite_count"),
    FAVORITED("favorited"),
    FAVOURITES_COUNT("favourites_count"),
    FILTER_LEVEL("filter_level"),
    FOLLOW_REQUEST_SENT("follow_request_sent"),
    FOLLOWERS_COUNT("followers_count"),
    FOLLOWING("following"),
    FRIENDS("friends"),
    FRIENDS_COUNT("friends_count"),
    FULL_NAME("full_name"),
    FULL_TEXT("full_text"),
    GEO("geo"),
    GEO_ENABLED("geo_enabled"),
    H("h"),
    HASHTAGS("hashtags"),
    ID("id"),
    ID_STR("id_str"),
    IN_REPLY_TO_SCREEN_NAME("in_reply_to_screen_name"),
    IN_REPLY_TO_STATUS_ID("in_reply_to_status_id"), //this is a retweet reply
    IN_REPLY_TO_STATUS_ID_STR("in_reply_to_status_id_str"),
    IN_REPLY_TO_USER_ID("in_reply_to_user_id"),
    IN_REPLY_TO_USER_ID_STR("in_reply_to_user_id_str"),
    INDICES("indices"),
    IS_QUOTE_STATUS("is_quote_status"),
    IS_TRANSLATOR("is_translator"),
    LANG("lang"),
    LARGE("large"),
    LISTED_COUNT("listed_count"),
    LOCATION("location"),
    MEDIA("media"),
    MEDIA_URL("media_url"),
    MEDIA_URL_HTTPS("media_url_https"),
    MEDIUM("medium"),
    MESSAGE("message"),
    NAME("name"),
    NOTIFICATIONS("notifications"),
    PLACE("place"),
    PLACE_TYPE("place_type"),
    POSSIBLY_SENSITIVE("possibly_sensitive"),
    PROFILE_BACKGROUND_COLOR("profile_background_color"),
    PROFILE_BACKGROUND_IMAGE_URL("profile_background_image_url"),
    PROFILE_BACKGROUND_IMAGE_URL_HTTPS("profile_background_image_url_https"),
    PROFILE_BACKGROUND_TILE("profile_background_tile"),
    PROFILE_BANNER_URL("profile_banner_url"),
    PROFILE_IMAGE_URL("profile_image_url"),
    PROFILE_IMAGE_URL_HTTPS("profile_image_url_https"),
    PROFILE_LINK_COLOR("profile_link_color"),
    PROFILE_SIDEBAR_BORDER_COLOR("profile_sidebar_border_color"),
    PROFILE_SIDEBAR_FILL_COLOR("profile_sidebar_fill_color"),
    PROFILE_TEXT_COLOR("profile_text_color"),
    PROFILE_USE_BACKGROUND_IMAGE("profile_use_background_image"),
    PROTECTED("protected"),
    QUOTED_STATUS("quoted_status"),
    QUOTED_STATUS_ID("quoted_status_id"),
    QUOTED_STATUS_ID_STR("quoted_status_id_str"),
    RESIZE("resize"),
    RETWEET_COUNT("retweet_count"), //total count of retweets.
    RETWEETED("retweeted"), //retweeted by the authenticated user
    RETWEETED_STATUS("retweeted_status"),
    SCREEN_NAME("screen_name"),
    SIZES("sizes"),
    SMALL("small"),
    SOURCE("source"),
    SOURCE_STATUS_ID("source_status_id"),
    SOURCE_STATUS_ID_STR("source_status_id_str"),
    SOURCE_USER_ID("source_user_id"),
    SOURCE_USER_ID_STR("source_user_id_str"),
    STATUS("status"),
    STATUSES_COUNT("statuses_count"),
    SYMBOLS("symbols"),
    TEXT("text"),
    THUMB("thumb"),
    TIME_ZONE("time_zone"),
    TIMESTAMP_MS("timestamp_ms"),
    TRUNCATED("truncated"),
    TYPE("type"),
    URL("url"),
    URLS("urls"),
    USER("user"),
    USER_ID("user_id"),
    USER_ID_STR("user_id_str"),
    USER_MENTIONS("user_mentions"),
    UTC_OFFSET("utc_offset"),
    VARIANTS("variants"),
    VERIFIED("verified"),
    VIDEO_INFO("video_info"),
    W("w"),
    WARNING("warning");	
	
	String key;
	
	TwitterKey(String key) {
		this.key = key;
	}

	@Override
	public CharSequence getKey() {
		return key;
	}
}
