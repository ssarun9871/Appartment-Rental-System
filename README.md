# Apartment Rental System Entities

## 1. User
- **user_id**: Integer (Primary Key, Auto-generated)
- **username**: String (Unique, Not Null, 4-20 characters)
- **password**: String (Not Null, Minimum 8 characters)
- **role**: String (Not Null) - admin, tenant, landlord
- **status**: String (Not Null) - pending, accepted, rejected

## 2. Landlord
- **landlord_id**: Integer (Primary Key, Auto-generated)
- **first_name**: String (Not Null)
- **last_name**: String (Not Null)
- **mobile**: String (Not Null, 10 digits)
- **age**: Integer (Not Null, Minimum 18)
- **user**: User (One-to-One, Not Null, Foreign Key to `user_id`)
- **flats**: List<Flat> (One-to-Many, mapped by `landlord`)

## 3. Flat
- **flat_id**: Integer (Primary Key, Auto-generated)
- **address**: String (Not Null, 5-255 characters)
- **availability**: Boolean (Not Null)
- **rent**: Integer (Not Null, Positive Number)
- **landlord**: Landlord (Many-to-One, Foreign Key to `landlord_id`)
- **tenant**: Tenant (One-to-One, mapped by `flat`)

## 4. Tenant
- **tenant_id**: Integer (Primary Key, Auto-generated)
- **name**: String (Not Null)
- **contact**: String (Not Null, 10 digits)
- **email**: String (Not Null, Valid Email)
- **flat**: Flat (One-to-One, Not Null, Foreign Key to `flat_id`)

## 5. Booking
- **booking_id**: Integer (Primary Key, Auto-generated)
- **flat_id**: Integer (Not Null, Foreign Key to `flat_id`)
- **tenant_id**: Integer (Not Null, Foreign Key to `tenant_id`)
- **from_date**: LocalDate (Not Null)
- **to_date**: LocalDate (Not Null)
