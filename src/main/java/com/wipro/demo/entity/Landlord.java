package com.wipro.demo.entity;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Landlord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer landlord_id;

	@NotNull
	private String first_name;

	@NotNull
	private String last_name;

	@NotNull
	@Pattern(regexp = "\\d{10}", message = "Mobile number should be 10 digits")
	private String mobile;

	@NotNull
	@Min(value = 18, message = "Age should be >= 18")
	private Integer age;
	

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "landlord")
    private List<Flat> flats;

}
