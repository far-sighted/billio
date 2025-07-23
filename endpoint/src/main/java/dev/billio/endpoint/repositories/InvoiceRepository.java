package dev.billio.endpoint.repositories;

import dev.billio.endpoint.models.InvoiceModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceModel, UUID> {
    /**
     * Finds an invoice by its ID.
     *
     * @param invoiceNumber the ID of the invoice
     * @return an Optional containing the InvoiceModel if found, or empty if not found
     */
}
