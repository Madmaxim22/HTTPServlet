package repository;

import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
  public ConcurrentMap<Long, Post> postsMap = new ConcurrentHashMap<>();
  public AtomicLong counter = new AtomicLong();
  public List<Post> all() {
    return postsMap.values().stream().toList();
  }

  public Optional<Post> getById(long id) {
    return Optional.of(postsMap.get(id));
  }

  public Post save(Post post) {
    if(post.getId() == 0) {
      postsMap.put(counter.incrementAndGet(), post);
      post.setId(counter.get());
      return post;
    }
    if(postsMap.containsKey(post.getId())) {
      postsMap.put(post.getId(), post);
    } else {
      post.setId(0);
      this.save(post);
    }
    return post;
  }

  public void removeById(long id) {
    postsMap.remove(id);
  }
}
