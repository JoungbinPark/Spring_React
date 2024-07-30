package com.himedia.spServer.dao;

import com.himedia.spServer.entity.Images;
import com.himedia.spServer.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    List<Reply> findByPostid(int postid);

    List<Reply> findByPostidOrderByIdDesc(int postid);
}
