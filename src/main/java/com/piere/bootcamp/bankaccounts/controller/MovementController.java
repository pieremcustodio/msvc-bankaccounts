package com.piere.bootcamp.bankaccounts.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piere.bootcamp.bankaccounts.model.dto.MovementDto;
import com.piere.bootcamp.bankaccounts.service.MovementService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController 
@RequestMapping("/api/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    /**
     * POST /api/movements : Create a movement
     * Create a new movement
     *
     * @param movementDto  (required)
     * @return Movement created (status code 201)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Create a movement", nickname = "createMovement", notes = "Create a new movement", response = MovementDto.class, tags={ "movements", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Movement created"),
        @ApiResponse(code = 400, message = "Bad request") })
    @PostMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<MovementDto>> createMovement(@ApiParam(value = "" ,required=true )  @Valid @RequestBody MovementDto movementDto) {
        return movementService.create(movementDto)
                .map(movement -> ResponseEntity.created(URI.create("/api/movements")).body(movement));
    }

    /**
     * DELETE /api/movements : Delete a movement
     * Delete an existing movement
     *
     * @param movementDto  (required)
     * @return Movement deleted (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Delete a movement", nickname = "deleteMovement", notes = "Delete an existing movement", response = MovementDto.class, tags={ "movements", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Movement deleted"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @DeleteMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<Void>> deleteMovement(@ApiParam(value = "" ,required=true )  @Valid @RequestBody MovementDto movementDto) {
        return movementService.delete(movementDto)
                .map(movement -> ResponseEntity.ok().build());
    }

    /**
     * PUT /api/movements : Update a movement
     * Update an existing movement
     *
     * @param movementDto  (required)
     * @return Movement updated (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Update a movement", nickname = "updateMovement", notes = "Update an existing movement", response = MovementDto.class, tags={ "movements", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Movement updated"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @PutMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<MovementDto>> updateMovement(@ApiParam(value = "" ,required=true )  @Valid @RequestBody MovementDto movementDto) {
        return movementService.update(movementDto)
                .map(movement -> ResponseEntity.ok().body(movement));
    }

    /**
     * GET /api/movements : Get all movements
     * Use to request all movements
     *
     * @return A list of movements (status code 200)
     */
    @ApiOperation(value = "Get all movements", nickname = "findAllMovements", notes = "Use to request all movements", response = MovementDto.class, responseContainer = "List", tags={ "movements", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of movements", response = MovementDto.class, responseContainer = "List") })
    @GetMapping(
        produces = { "application/json" }
    )
    Mono<ResponseEntity<Flux<MovementDto>>> findAllMovements() {
        return Mono.just(ResponseEntity.ok(movementService.findAll()));
    }

}
