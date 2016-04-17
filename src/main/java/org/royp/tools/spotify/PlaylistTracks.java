package org.royp.tools.spotify;

import java.util.List;

public class PlaylistTracks {
	public static class Item {
		public static class Track {
			public static class Artist {
				public Object external_urls;
				public String href;
				public String id;
				public String name;
				public String type;
				public String uri;
			}
			public Object album;
			public List<Artist> artists;
			public Object available_markets;
			public Object disc_number;
			public Object duration_ms;
			public Object explicit;
			public Object external_ids;
			public Object external_urls;
			public Object href;
			public Object id;
			public Object name;
			public Object popularity;
			public Object preview_url;
			public Object track_number;
			public Object type;
			public Object uri;
		}
		public String added_at;
		public Object added_by;
		public boolean is_local;
		public Track track;
	}
	public String href;
	public List<Item> items;
	public long limit;
	public String next;
	public long offset;
	public String previous;
	public long total;
}
