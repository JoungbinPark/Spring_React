package com.himedia.spServer.dao;

import com.himedia.spServer.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("select p from Post p where p.id IN (select ph.postid from Posthash ph where ph.hashid =:hashid) order by p.id desc" )
    List<Post> getPostListByTag(int hashid);
    List<Post> findByWriterOrderByIdDesc(String nickname);
}
