package io.github.iurimenin.goldenraspberryawards.repository;

import io.github.iurimenin.goldenraspberryawards.domain.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findAllByWinnerTrue();
}