# Appartment Rental System

This application manages rental systems for apartments. It allows for the management of users (Admins, Owners, and Tenants), flats, and bookings.

## Entities

### User
Represents a user in the system. Users can be Admins, Owners (Landlords), or Tenants.

- **id**: `Integer` - Unique identifier for the user.
- **username**: `String` - The username of the user. (Not Null, Size: 4-20)
- **password**: `String` - The password of the user. (Not Null, Size: Minimum 8)
- **role**: `String` - The role of the user. (Not Null) Possible values: "admin", "landlord", "tenant".
- **status**: `String` - The status of the user. (Not Null) Possible values: "pending", "accepted", "rejected".

### Flat
Represents a flat or apartment in the system.

- **id**: `Integer` - Unique identifier for the flat.
- **address**: `String` - The address of the flat. (Not Null)
- **status**: `String` - The status of the flat. (Not Null) Possible values: "available", "occupied".
- **ownerId**: `Integer` - The ID of the user who owns the flat. (Not Null)

### Booking
Represents a booking made by a tenant for a flat.

- **id**: `Integer` - Unique identifier for the booking.
- **tenantId**: `Integer` - The ID of the tenant who made the booking. (Not Null)
- **flatId**: `Integer` - The ID of the flat that is booked. (Not Null)
- **status**: `String` - The status of the booking. (Not Null) Possible values: "pending", "confirmed", "cancelled".

