package dev.billio.endpoint.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dbo_datatable_invoice")
public class InvoiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uid;

    @Column (name = "i_invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @Column(name = "i_invoice_type", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'INVOICE'")
    private String invoiceType;

    @Column(name = "i_invoice_creation_date", nullable = false)
    private Date invoiceCreationDate;

    @Column(name = "i_invoice_due_date", nullable = false)
    private Date invoiceDueDate;

    @Column(name = "i_invoice_total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "i_invoice_amount_paid", nullable = false)
    private Double amountPaid;

    @Column (name="i_invoice_status", nullable = false)
    private String invoiceStatus;
}
