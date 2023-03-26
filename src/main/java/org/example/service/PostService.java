package org.example.service;

import org.example.exception.NotFoundException;
import org.example.repository.PostRepositoryStubImpl;
import org.example.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
  private final PostRepositoryStubImpl repository;

  public PostService(PostRepositoryStubImpl repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id).orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}

