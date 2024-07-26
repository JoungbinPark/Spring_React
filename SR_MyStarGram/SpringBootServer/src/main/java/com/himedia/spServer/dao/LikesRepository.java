package com.himedia.spServer.dao;

import com.himedia.spServer.entity.Images;
import com.himedia.spServer.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
    List<Likes> findByPostid(int postid);

    Optional<Likes> findByPostidAndLikenick(int postid, String likenick);
}
