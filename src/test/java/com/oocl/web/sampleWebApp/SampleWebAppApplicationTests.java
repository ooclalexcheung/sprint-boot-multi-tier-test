package com.oocl.web.sampleWebApp;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.oocl.web.sampleWebApp.WebTestUtil.getContentAsObject;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleWebAppApplicationTests {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private MockMvc mvc;

	@Test
	public void should_get_parking_boys() throws Exception {
	    // Given
        final ParkingBoy boy = parkingBoyRepository.save(new ParkingBoy("boy"));

        // When
        final MvcResult result = mvc.perform(MockMvcRequestBuilders
            .get("/parkingboys"))
            .andReturn();

        // Then
        assertEquals(200, result.getResponse().getStatus());

        final ParkingBoyResponse[] parkingBoys = getContentAsObject(result, ParkingBoyResponse[].class);

        assertEquals(1, parkingBoys.length);
        assertEquals("boy", parkingBoys[0].getEmployeeId());
    }

    @Test
    public void should_add_new_parking_boy() throws Exception {
        //given
        int newParkingId = 1;
        String newParkingBoyInJson = "{\"employeeId\":" + newParkingId + "}";
        //when
        mvc.perform(post("/parkingboys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newParkingBoyInJson)
        )//then
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/parkingboys/"+newParkingId)));
    }

    @Test
    public void should_not_create_parking_boy_with_input_empty_string() throws Exception{
        //Given a empty employeeId
        String createTestParkingBoyJson = "{\"employeeId\":}";
        //When POST to /parkingboys
        mvc.perform(post("/parkingboys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createTestParkingBoyJson)
        )
                //Then it should return 400 Bad Request
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_400_for_long_employeeId() throws Exception{
	    //Given a long employeeId
        final String longEmployeeId = "0123456789012345678901234567890123456789012345678901234567890123456789";
        mvc.perform(post("/parkingboys")
                .content(String.format("{\"employeeId\": \"%s\"}", longEmployeeId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}
