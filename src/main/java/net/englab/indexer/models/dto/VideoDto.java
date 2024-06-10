package net.englab.indexer.models.dto;

import net.englab.common.search.models.common.EnglishVariety;

/**
 * A data transfer object record that represents video.
 *
 * @param videoId   the YouTube video ID
 * @param variety   the variety of English
 * @param srt       the video subtitles in the SRT format
 */
public record VideoDto(String videoId, EnglishVariety variety, String srt) {
}
