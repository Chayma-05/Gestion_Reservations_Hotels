// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: reservation.proto

package com.example.gestion_reservations_hotels.stubs;

public interface CreateReservationRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:reservation.CreateReservationRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string client_name = 1;</code>
   * @return The clientName.
   */
  java.lang.String getClientName();
  /**
   * <code>string client_name = 1;</code>
   * @return The bytes for clientName.
   */
  com.google.protobuf.ByteString
      getClientNameBytes();

  /**
   * <code>int32 room_number = 2;</code>
   * @return The roomNumber.
   */
  int getRoomNumber();

  /**
   * <code>string start_date = 3;</code>
   * @return The startDate.
   */
  java.lang.String getStartDate();
  /**
   * <code>string start_date = 3;</code>
   * @return The bytes for startDate.
   */
  com.google.protobuf.ByteString
      getStartDateBytes();

  /**
   * <code>string end_date = 4;</code>
   * @return The endDate.
   */
  java.lang.String getEndDate();
  /**
   * <code>string end_date = 4;</code>
   * @return The bytes for endDate.
   */
  com.google.protobuf.ByteString
      getEndDateBytes();
}
