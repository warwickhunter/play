cmake_minimum_required (VERSION 3.1.0)

project (PolymorphicShapes)
add_executable(PolymorphicShapes PolymorphicShapes.cpp)
target_compile_features(PolymorphicShapes PRIVATE cxx_range_for)
