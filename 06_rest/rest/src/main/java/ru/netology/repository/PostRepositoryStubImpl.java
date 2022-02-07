package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
  public Map<Long, Post> posts = new HashMap<>();
  private long id = 1;

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {

    return Optional.ofNullable(posts.get(id));
  }

  public synchronized Post save(Post post) {

    if (post.getId() == 0){
      Post newPost = new Post(id, post.getContent());
      posts.put(id, newPost);
      id++;
      return newPost;
    }
    posts.remove(post.getId());
    Post newPost = new Post(post.getId(), post.getContent());
    posts.put(post.getId(), newPost);
    return newPost;
  }

  public synchronized void removeById(long id) {
    posts.remove(id);
  }
}