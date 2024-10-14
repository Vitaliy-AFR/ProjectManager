package com.example.ProjectManager;

import com.example.ProjectManager.controller.ProjectController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProjectControllerTest  {

    @Autowired
    private ProjectController controller;

    @Test
    public void test() throws Exception {
        assertThat(controller).isNotNull();
    }

}
