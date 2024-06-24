package br.com.alura.Forum_Hub.infra.controller;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicDetailedDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicListDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.infra.service.topic.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/register")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> registerTopic(@RequestBody @Valid TopicRegisterDataDTO dataDTO, UriComponentsBuilder builder){

        Topic topic = topicService.createTopicObject(dataDTO);

        URI uri = builder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDetailedDataDTO(topic));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<TopicListDataDTO>> listTopics(@PageableDefault(size = 10, sort = "course") Pageable pageable){
        return ResponseEntity.ok().body(topicService.listTopicsPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailedDataDTO> showDetailedData(@PathVariable Long id){
        return ResponseEntity.ok(topicService.getTopicById(id));
    }
}
