package com.himedia.spServer.service;

import com.himedia.spServer.dao.*;
import com.himedia.spServer.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class PostService {

    @Autowired
    PostRepository pr;

    public Object getPostList() {
        return pr.findAll( Sort.by(Sort.Direction.DESC, "id"));
        // pr.findAll( Sort.by(Sort.Direction.DESC, "id", "writer"));
    }

    @Autowired
    ImagesRepository ir;
    @Autowired
    LikesRepository lr;
    @Autowired
    ReplyRepository rr;

    public List<Images> getImages(int postid) {
        return ir.findByPostid(postid);
    }

    public List<Likes> getLikes(int postid) {
        return lr.findByPostid(postid);
    }

    public List<Reply> getReplys(int postid) {
        return rr.findByPostidOrderByIdDesc(postid);
    }

    public void addlike(int postid, String likenick) {
        Optional<Likes> rec = lr.findByPostidAndLikenick(postid, likenick);
        if(rec.isPresent()){
            lr.delete(rec.get());
        }else{
            Likes l = new Likes();
            l.setPostid(postid);
            l.setLikenick(likenick);
            lr.save(l);
        }
    }

    public void insertReply(Reply reply) {
        rr.save(reply);
    }

    public void deleteReply(int id) {
        Optional<Reply> reply = rr.findById(id);
        if(reply.isPresent()){
            rr.delete(reply.get());
        }
    }

    @Autowired
    HashtagRepository hr;

    @Autowired
    PosthashRepository phr;

    public Post insertPost(Post post) {
        // 포스트 추가
        Post p = pr.save(post);
        int postid = p.getId();
        
        //포스트의 content 추출
        String content = post.getContent();
        // content에서 해시태그들만 추출
        Matcher m = Pattern.compile("#(0-9a-zA-Z가-힣]*)").matcher(content);
        List<String> tags = new ArrayList<String>();
        while(m.find()){
            System.out.println(m.group(1));
            tags.add(m.group(1));
        }
        System.out.println(tags);
        //추출된 해시태그들로 해시태그 작업
        int tagid = 0;
        for(String word : tags){
            Optional<Hashtag> rec = hr.findByWord(word);
            // 현재 워드가 없으면 hashtag 테이블에 새 레코드 추가
            if(!rec.isPresent()){
                Hashtag hdnew = new Hashtag();
                hdnew.setWord(word);
                Hashtag hdsave = hr.save(hdnew);
                tagid = hdsave.getId();
            }else {
                tagid = rec.get().getId();
                // 있으면 아이디만 추출
            }
            // 추출된 포스트아이디와 해시태그아이디로 posthash 테이블에 레코드 추가
            Posthash ph = new Posthash();
            ph.setPostid(postid);
            ph.setHashid(tagid);
            phr.save(ph);
        }
        return p;
    }

    public void insertImages(Images images) {
    }
}
