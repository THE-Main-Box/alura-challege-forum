package br.com.alura.Forum_Hub.infra.controller;

import br.com.alura.Forum_Hub.domain.dto.like.LikeDetailedDataDTO;
import br.com.alura.Forum_Hub.domain.dto.like.LikeListDataDTO;
import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicDetailedDataDTO;
import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.repository.LikeRepository;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import br.com.alura.Forum_Hub.infra.service.like.LikeService;
import br.com.alura.Forum_Hub.infra.service.topic.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeService likeService;


    @PutMapping("/topic")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> likeTopic(@RequestBody LikeRegisterDataDTO dataDTO) {
        return ResponseEntity.ok().body(new LikeDetailedDataDTO(topicService.LikeTopic(dataDTO)));
    }

    @GetMapping("/list")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<LikeDetailedDataDTO>> listLikesFrom(@RequestBody @Valid LikeListDataDTO dataDTO, Pageable page) {
        return ResponseEntity.ok().body(
                likeRepository.findByLikableEntityAndLikedItemId(dataDTO.entityType(),dataDTO.entityId(), page)
                        .map(LikeDetailedDataDTO::new)
        );
    }

    @DeleteMapping("/dislike/topic")
    @SecurityRequirement(name="bearer-key")
    public ResponseEntity<Object> dislikeTopic(@RequestBody LikeRegisterDataDTO dataDTO){
        likeService.dislikeTopic(dataDTO);
        return ResponseEntity.ok().body("dislike dado com sucesso");
    }

}
