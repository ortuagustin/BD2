package ar.edu.info.unlp.bd2.etapa2.utils;

import org.springframework.data.annotation.Id;

import ar.edu.info.unlp.bd2.etapa2.model.ReservationStatus;

public class ReservationCount {
  @Id
  private ReservationStatus status;
  private Long count;

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public ReservationStatus getStatus() {
    return status;
  }

  public void setStatus(ReservationStatus status) {
    this.status = status;
  }
}
