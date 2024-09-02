package it.ezzie.myapplist.voucher;

import java.io.Serializable;

public record Voucher(int id ,String title, int quantity, int amount) implements Serializable {
}
