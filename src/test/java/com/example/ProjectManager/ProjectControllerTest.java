package com.example.ProjectManager;

import com.example.ProjectManager.controller.ProjectController;
import com.example.ProjectManager.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest  {

    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void loginTest() throws Exception {
//        this.mockMvc.perform(get("/api/v1/projects"))
//                .andExpect(status().is3xxRedirection());
//    }


    @Test
    @WithMockUser(roles = "ADMIN", password = "admin", username = "admin")
    public void findAllProjectsTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/projects"))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
