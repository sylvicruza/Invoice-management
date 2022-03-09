package com.example.demo.controllers.apis;

import com.example.demo.models.entity.Client;
import com.example.demo.models.entity.Invoice;
import com.example.demo.models.entity.ItemInvoice;
import com.example.demo.models.entity.Product;
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
@RequestMapping(value = "/api/v1/invoice", produces = "application/json")
@Api(tags = "Invoice Management")
public class ApiInvoiceController {
    private static final String MESSAGE = "Client with email address already exist";
    private HttpHeaders responseHeaders = new HttpHeaders();
    private static final String GET_DATA_SUCCESSFULLY = "Get data successfully";
    private static final String SAVE_DATA_SUCCESSFULLY = "Save data successfully";
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private IClientService iClientService;

    public ApiInvoiceController(IClientService iClientService) {
        this.iClientService = iClientService;
    }

    @ApiOperation(value = "Add a client", response = ServerResponse.class)
    @PostMapping(path = "/all")
    @ResponseBody
    public ResponseEntity createInvoiceUser(@RequestParam Long clientId) {

        ServerResponse response;
        try {
            Client client = iClientService.findOne(clientId);
            if(client!=null){
                response = ServerResponse.badRequest("Client not found");

            }

            Invoice invoice = new Invoice();
            invoice.setClient(client);
            iClientService.saveInvoice(invoice);
            response = new ServerResponse(true,
                    SAVE_DATA_SUCCESSFULLY,
                    invoice,
                    ServerResponseStatus.OK);
        } catch (Exception e) {
            response = exceptionMessage(e);
        }

        return new ResponseEntity<>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
    }

    @ApiOperation(value = "Get all invoice", response = ServerResponse.class)
    @PostMapping(path = "/summary")
    @ResponseBody
    public ResponseEntity getInvoiceSummary(@RequestParam Invoice invoice, @RequestParam(name = "item_id[]", required = false) Long[] itemId, @RequestParam(name = "amount[]", required = false) Integer[] amount) {
        ServerResponse response;
        try {
            if (itemId == null || itemId.length == 0) {

            }
            for (int i = 0; i < itemId.length; i++) {
                Product product = iClientService.findProductById(itemId[i]);

                ItemInvoice line = new ItemInvoice();
                line.setAmount(amount[i]);
                line.setProduct(product);
                invoice.addItemInvoice(line);

                logger.info("ID: {}  amount: {}", itemId[i].toString() , amount[i].toString());
            }

            iClientService.saveInvoice(invoice);

            response = new ServerResponse(true,
                    GET_DATA_SUCCESSFULLY,
                    invoice,
                    ServerResponseStatus.OK);
        } catch (Exception e) {
            response = exceptionMessage(e);
        }

        return new ResponseEntity<>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
    }

    @ApiOperation(value = "Get invoice by ID", response = ServerResponse.class)
    @GetMapping(path = "/view/{id}")
    @ResponseBody
    public ResponseEntity getInvoiceById(@PathVariable(value = "id") Long id) {
        ServerResponse response;
        try {
            response = new ServerResponse(true,
                    GET_DATA_SUCCESSFULLY,
                    iClientService.findInvoiceById(id),
                    ServerResponseStatus.OK);
        } catch (Exception e) {
            response = exceptionMessage(e);
        }

        return new ResponseEntity<>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
    }

    @ApiOperation(value = "Delete invoice", response = ServerResponse.class)
    @GetMapping(path = "/delete/{id}")
    @ResponseBody
    public ResponseEntity deleteInvoiceById(@PathVariable(value = "id") Long id) {
        ServerResponse response;

        try {
            if(id!=null){
                iClientService.deleteInvoice(id);
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

