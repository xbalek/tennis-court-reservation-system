classDiagram
direction BT
class AlreadyReservedException
class AppConfig
class AuthController
class Customer
class CustomerController
class CustomerDTO
class CustomerMapper {
<<Interface>>

}
class CustomerRepository
class CustomerRepositoryInterface {
<<Interface>>

}
class CustomerRole {
<<enumeration>>

}
class CustomerService
class CustomerServiceInterface {
<<Interface>>

}
class DataInitializer
class JWTHeaderValidator
class JWTProvider
class LoginRequest
class NotFoundException
class Reservation
class ReservationController
class ReservationDTO
class ReservationMapper {
<<Interface>>

}
class ReservationRepository
class ReservationRepositoryInterface {
<<Interface>>

}
class ReservationService
class ReservationServiceInterface {
<<Interface>>

}
class SecurityConfig
class SurfaceType
class SurfaceTypeController
class SurfaceTypeDTO
class SurfaceTypeEnum {
<<enumeration>>

}
class SurfaceTypeMapper {
<<Interface>>

}
class SurfaceTypeRepository
class SurfaceTypeRepositoryInterface {
<<Interface>>

}
class SurfaceTypeService
class SurfaceTypeServiceInterface {
<<Interface>>

}
class TennisCourt
class TennisCourtController
class TennisCourtDTO
class TennisCourtMapper {
<<Interface>>

}
class TennisCourtRepository
class TennisCourtRepositoryInterface {
<<Interface>>

}
class TennisCourtReservationSystemApplication
class TennisCourtService
class TennisCourtServiceInterface {
<<Interface>>

}
class WebConfig

CustomerRepository  ..>  CustomerRepositoryInterface 
CustomerService  ..>  CustomerServiceInterface 
ReservationRepository  ..>  ReservationRepositoryInterface 
ReservationService  ..>  ReservationServiceInterface 
SurfaceTypeRepository  ..>  SurfaceTypeRepositoryInterface 
SurfaceTypeService  ..>  SurfaceTypeServiceInterface 
TennisCourtRepository  ..>  TennisCourtRepositoryInterface 
TennisCourtService  ..>  TennisCourtServiceInterface 
