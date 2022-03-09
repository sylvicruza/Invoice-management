package com.example.demo.controllers.apis;

import com.example.demo.models.entity.Client;
import com.example.demo.models.service.IClientService;
import com.example.demo.models.utils.ServerResponse;
import com.example.demo.models.utils.ServerResponseStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.models.utils.ServerResponse.exceptionMessage;

@RestController
@RequestMapping(value = "/api/v1/client", produces = "application/json")
@Api(tags = "Client Management")
public class ApiClientController {
    private static final String MESSAGE = "Client with email address already exist";
    private HttpHeaders responseHeaders = new HttpHeaders();
    private static final String GET_DATA_SUCCESSFULLY = "Get data successfully";
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private IClientService iClientService;

    public ApiClientController(IClientService iClientService) {
        this.iClientService = iClientService;
    }

    @ApiOperation(value = "Add a client", response = ServerResponse.class)
    @PostMapping(path = "/all")
    @ResponseBody
    public ResponseEntity createClientUser(@RequestBody Client payload) {
        Client client;
        ServerResponse response;
        try {
            client = iClientService.findByEmail(payload.getEmail());

            if(client==null){
                response = ServerResponse.badRequest(MESSAGE);

            }else {
                client = new Client();
                client.setCreateAt(payload.getCreateAt());
                client.setEmail(payload.getEmail());
                client.setId(payload.getId());
                client.setFirstName(payload.getFirstName());
                client.setInvoices(payload.getInvoices());
                client.setLastName(payload.getLastName());
                iClientService.save(client);
            }
            response = new ServerResponse(true,
                    GET_DATA_SUCCESSFULLY,
                    client,
                    ServerResponseStatus.OK);
        } catch (Exception e) {
            response = exceptionMessage(e);
        }

        return new ResponseEntity<>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
    }

    @ApiOperation(value = "Get all clients", response = ServerResponse.class)
    @GetMapping(path = "/all")
    @ResponseBody
    public ResponseEntity getClientUsers() {
        ServerResponse response;
        try {
            response = new ServerResponse(true,
                    GET_DATA_SUCCESSFULLY,
                    iClientService.findAll(),
                    ServerResponseStatus.OK);
        } catch (Exception e) {
            response = exceptionMessage(e);
        }

        return new ResponseEntity<>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
    }

    @ApiOperation(value = "Get client by ID", response = ServerResponse.class)
    @GetMapping(path = "/view/{id}")
    @ResponseBody
    public ResponseEntity getClientById(@PathVariable(value = "id") Long id) {
        ServerResponse response;
        try {
            response = new ServerResponse(true,
                    GET_DATA_SUCCESSFULLY,
                    iClientService.fetchByIdWithInvoices(id),
                    ServerResponseStatus.OK);
        } catch (Exception e) {
            response = exceptionMessage(e);
        }

        return new ResponseEntity<>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
    }

    @ApiOperation(value = "Delete client", response = ServerResponse.class)
    @GetMapping(path = "/delete/{id}")
    @ResponseBody
    public ResponseEntity deleteClientById(@PathVariable(value = "id") Long id) {
        ServerResponse response;

        try {
            if(id!=null){
                iClientService.delete(id);
            }
            response = new ServerResponse(true,
                    GET_DATA_SUCCESSFULLY,new Client()
                    ,
                    ServerResponseStatus.OK);
        } catch (Exception e) {
            response = exceptionMessage(e);
        }

        return new ResponseEntity<>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
    }



}
