package com.himedia.spServer.controller;

import com.himedia.spServer.entity.Images;
import com.himedia.spServer.entity.Likes;
import com.himedia.spServer.entity.Post;
import com.himedia.spServer.entity.Reply;
import com.himedia.spServer.service.PostService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService ps;

    @GetMapping("getPostList")
    public HashMap<String, Object> getPostList(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("postList", ps.getPostList());
        return result;
    }

    @GetMapping("/getImages/{postid}")
    List<Images> getImages(@PathVariable("postid") int postid){
        List<Images> list = ps.getImages(postid);
        return list;
    }

    @GetMapping("/getLikes/{postid}")
    List<Likes> getLikes(@PathVariable("postid") int postid) {
        List<Likes> list = ps.getLikes(postid);
        return list;
    }

    @GetMapping("/getReplys/{postid}")
    List<Reply> getReplys(@PathVariable("postid") int postid){
        List<Reply> list = ps.getReplys(postid);
        return list;
    }

    @PostMapping("/addlike")
    public HashMap<String, Object> addlike(@RequestParam("postid") int postid, @RequestParam("likenick") String likenick){
        ps.addlike(postid, likenick);
        return null;
    }

    @PostMapping("/addReply")
    public HashMap<String, Object> addReply(@RequestBody Reply reply){
        ps.insertReply(reply);
        return null;
    }

    @DeleteMapping("/deleteReply/{id}")
    public HashMap<String, Object> deleteReply(@PathVariable("id") int id){
        ps.deleteReply( id );
        return null;
    }

    @Autowired
    ServletContext context;

    @PostMapping("/imgup")
    public HashMap<String, Object> fileup(@RequestParam("image") MultipartFile file){
        HashMap<String, Object> result = new HashMap<String, Object>();
        String path = context.getRealPath("/uploads");
        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();

        String filename = file.getOriginalFilename();
        String f1 = filename.substring(0, filename.indexOf("."));
        String f2 = filename.substring(filename.lastIndexOf("."));
        String uploadPath = path + "/" + f1 + dt + f2;

        try {
            file.transferTo(new File(uploadPath));
            result.put("savefilename", f1 + dt + f2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @PostMapping("/writePost")
    public HashMap<String, Object> writePost(@RequestBody Post post){
        HashMap<String, Object> result = new HashMap<>();
        Post p = ps.insertPost(post);
        result.put("postid", p.getId());
        return result;
    }

    @PostMapping("/writeimages")
    public HashMap<String, Object> writeImages(@RequestBody Images images){
        HashMap<String, Object> result = new HashMap<>();
        ps.insertImages(images);
        return result;
    }
}
