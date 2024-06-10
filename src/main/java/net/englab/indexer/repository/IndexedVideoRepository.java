package net.englab.indexer.repository;

import net.englab.indexer.models.entities.IndexedVideo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface provides methods for querying indexed video objects
 * from the database. It extends JpaRepository for standard CRUD operations
 * on video entities.
 */
public interface IndexedVideoRepository extends JpaRepository<IndexedVideo, Long> {

    /**
     * Deletes a video by its YouTube video ID.
     *
     * @param indexName         the name of the index
     * @param youtubeVideoId    the YouTube video ID
     */
    void deleteByIndexNameAndYoutubeVideoId(String indexName, String youtubeVideoId);

    /**
     * Deletes all videos that do not belong to the specified index.
     *
     * @param indexName the name of the index
     */
    void deleteAllByIndexNameIsNot(String indexName);
}
