add_executable(SimpleDB main.cpp Database.cpp)
include_directories(include)

set_target_properties(
  SimpleDB PROPERTIES CXX_STANDARD 17 CXX_STANDARD_REQUIRED ON
)