# Entity Definitions

## Admin
- **id**: `Integer` (Primary Key)
- **username**: `String` (Unique, Not Null, Size between 4 to 20 characters)
- **password**: `String` (Not Null, Minimum 8 characters)

## Landlord
- **landlord_id**: `Integer` (Primary Key)
- **username**: `String` (Not Null)
- **password**: `String` (Not Null)
- **first_name**: `String` (Not Null)
- **last_name**: `String` (Not Null)
- **mobile**: `String` (Not Null, 10 digits)
- **age**: `Integer` (Not Null, Minimum 18)
- **signup_status**: `String` (Not Null)
- **blocked**: `Boolean` (Not Null)
- **flats**: `List<Flat>` (One-to-Many relationship with `Flat`)

## Tenant
- **tenant_id**: `Integer` (Primary Key)
- **username**: `String` (Not Null)
- **password**: `String` (Not Null)
- **first_name**: `String` (Not Null)
- **last_name**: `String` (Not Null)
- **mobile**: `String` (Not Null, 10 digits)
- **age**: `Integer` (Not Null, Minimum 18)
- **signup_status**: `String` (Not Null)
- **blocked**: `Boolean` (Not Null)
- **flat**: `Flat` (One-to-One relationship with `Flat`)
- **bookings**: `List<Booking>` (One-to-Many relationship with `Booking`)

## Flat
- **flat_id**: `Integer` (Primary Key)
- **address**: `String` (Not Null, Size between 5 to 255 characters)
- **availability**: `Boolean` (Not Null)
- **rent**: `Integer` (Not Null, Positive)
- **landlord**: `Landlord` (Many-to-One relationship with `Landlord`)
- **tenant**: `Tenant` (One-to-One relationship with `Tenant`)
- **bookings**: `List<Booking>` (One-to-Many relationship with `Booking`)

## Booking
- **booking_id**: `Integer` (Primary Key)
- **flat**: `Flat` (Many-to-One relationship with `Flat`)
- **tenant**: `Tenant` (Many-to-One relationship with `Tenant`)
- **from_date**: `LocalDate` (Not Null)
- **to_date**: `LocalDate` (Not Null)
- **booking_status**: `String` (Not Null)
