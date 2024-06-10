package net.englab.indexer.services;

import lombok.RequiredArgsConstructor;
import net.englab.indexer.models.entities.IndexedVideo;
import net.englab.indexer.repository.IndexedVideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A storage service for indexed videos. It provides basic operations
 * such as saving, deleting, and searching subtitles.
 */
@Service
@RequiredArgsConstructor
public class IndexedVideoStorage {

    private final IndexedVideoRepository indexedVideoRepository;

    /**
     * Saves a new video to the storage.
     *
     * @param indexedVideo the video that needs to be saved
     */
    @Transactional
    public void save(IndexedVideo indexedVideo) {
        indexedVideoRepository.save(indexedVideo);
    }

    /**
     * Deletes a video by its YouTube video ID.
     *
     * @param indexName         the name of the index
     * @param youtubeVideoId    the YouTube video ID
     */
    @Transactional
    public void delete(String indexName, String youtubeVideoId) {
        indexedVideoRepository.deleteByIndexNameAndYoutubeVideoId(indexName, youtubeVideoId);
    }

    /**
     * Removes stale videos that are left from previous indexations.
     *
     * @param indexName the current index name
     */
    @Transactional
    public void cleanUp(String indexName) {
        indexedVideoRepository.deleteAllByIndexNameIsNot(indexName);
    }
}
