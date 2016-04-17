package org.royp.tools.spotify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class SpotifyTool {
	private static final String OAUTH_TOKEN = "Bearer BQBr_pdd0HHBqhTM8LCkAPc6G5i4Zqi1XlpZ5dlYi2Z5JCbGD-tZyeDdUSi0cIzJMqTpWFbjhbjF8mRQo2Tw2FQT1KIpoCROpNL7pzUs6yjpWKKas1GzLziQPIb2tG_KD5NYo8-e55UXA16mw-6jLHVjGAHxPeNWZLM";
	private static final String BASE_URL = "https://api.spotify.com/v1/users/patroytall/playlists/56m0i9j5PmHzbdnrPWQPuL/tracks";

	private static final long MAXIMUM_LIMIT = 100;

	private RestTemplate restTemplate = new RestTemplate();
	private List<String> tracks = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		SpotifyTool spotifyTool = new SpotifyTool();
		spotifyTool.run();
	}

	private void run() {
		String url = BASE_URL + "?limit=" + MAXIMUM_LIMIT + "&offset=" + 0;
		PlaylistTracks playListTracks = getPlaylistTracks(url );
		addPlaylistTracks(playListTracks);
		while (playListTracks.next != null) {
			playListTracks = getPlaylistTracks(playListTracks.next);			
			addPlaylistTracks(playListTracks);
		}
		Collections.sort(tracks);
		for (String track : tracks) {
			System.out.println(track);		
		}
	}

	private void addPlaylistTracks(PlaylistTracks playListTracks) {
		for (PlaylistTracks.Item item : playListTracks.items) {
			PlaylistTracks.Item.Track track = item.track; 
			boolean first = true;
			StringBuilder sb = new StringBuilder();
			for (PlaylistTracks.Item.Track.Artist artist : track.artists) {
				if (first) {
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(artist.name);
			}
			sb.append("  -  ");
			sb.append(track.name);
			tracks.add(sb.toString());
		}
	}

//	private void addPlaylistTracks(PlaylistTracks playListTracks) {
//		for (PlaylistTracks.Item item : playListTracks.items) {
//			PlaylistTracks.Item.Track track = item.track; 
//			boolean first = true;
//			for (PlaylistTracks.Item.Track.Artist artist : track.artists) {
//				if (first) {
//					first = false;
//				} else {
//					print(", ");
//				}
//				print(artist.name);
//			}
//			println(" - " + track.name + "  -  ");
//		}
//	}

	private PlaylistTracks getPlaylistTracks(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", OAUTH_TOKEN);

		HttpEntity<PlaylistTracks> entity = new HttpEntity<PlaylistTracks>(
				headers);

		ResponseEntity<PlaylistTracks> playlistTracks = restTemplate.exchange(
				url, HttpMethod.GET, entity, PlaylistTracks.class);
		return playlistTracks.getBody();
	}
}