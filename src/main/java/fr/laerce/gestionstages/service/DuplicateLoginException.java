package fr.laerce.gestionstages.service;

public class DuplicateLoginException extends RuntimeException {
  public DuplicateLoginException(String message) {
    super(message);
  }
}
