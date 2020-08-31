package org.mytoshika.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mytoshika.exception.HotelNotFoundException;
import org.mytoshika.model.Hotel;
import org.mytoshika.repository.HotelRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	private final HotelRepository repository;

	public HotelController(HotelRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/table")
	public ResponseEntity<String> createTable() {
		repository.createTable();
		return ResponseEntity.ok("Table Created!");
	}

	@GetMapping
	public List<Hotel> readAll() {
		return Stream.generate(() -> repository.findAll().iterator().next()).collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel, UriComponentsBuilder uriComponentsBuilder) {
		Hotel savedHotel = repository.save(hotel);
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = uriComponentsBuilder
				.path("/hotels/")
				.path(String.valueOf(savedHotel.getId()))
				.build()
				.toUri();
		headers.setLocation(locationUri);

		return new ResponseEntity<>(savedHotel, headers, HttpStatus.CREATED);
	}

	@GetMapping("/{hotelId}")
	public Hotel readHotelById(@PathVariable("hotelId") String id) {
		return repository.findById(id).orElseThrow(HotelNotFoundException::new);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Hotel updateHotel(@RequestBody Hotel hotel) {

		repository.findById(hotel.getId()).orElseThrow(HotelNotFoundException::new);

		return repository.save(hotel);
	}

	@DeleteMapping("/{hotelId}")
	public void deleteHotel(@PathVariable("hotelId") String id) {
		repository.deleteById(id);
	}
	
	@DeleteMapping
	public void deleteAllHotel() {
		repository.deleteAll();
	}

}
