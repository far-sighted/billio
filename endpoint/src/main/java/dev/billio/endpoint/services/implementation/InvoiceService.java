package dev.billio.endpoint.services.implementation;

import dev.billio.endpoint.models.InvoiceModel;
import dev.billio.endpoint.repositories.InvoiceRepository;
import dev.billio.endpoint.services.interfaces.InvoiceInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Service
public class InvoiceService implements InvoiceInterface {

    //region Fields
    private final InvoiceRepository invoiceRepository;
    //endregion Fields

    /**
     * Constructs an InvoiceService with the specified InvoiceRepository.
     *
     * @param invoiceRepository the InvoiceRepository to be used by this service
     */

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Retrieves a paginated list of invoices.
     *
     * @param pageable the pagination information
     * @return a Page containing InvoiceModel objects
     */
    @Override
    public Page<InvoiceModel> get(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    /**
     * Finds an invoice by UUID.
     *
     * @param uid the UUID of the invoice
     * @return the InvoiceModel object corresponding to the given UUID
     */
    @Override
    public InvoiceModel find(UUID uid) {
        return invoiceRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("Invoice not found with UUID: " + uid));
    }

    /**
     * Saves an invoice.
     *
     * @param invoice the InvoiceModel object to be saved
     * @return the saved InvoiceModel object
     */
    @Override
    public InvoiceModel save(InvoiceModel invoice) {
        return invoiceRepository.save(invoice);
    }

    /**
     * Deletes an invoice by UUID.
     *
     * @param uid the UUID of the invoice to be deleted
     */
    @Override
    public void delete(UUID uid) {
        if (!invoiceRepository.existsById(uid)) {
            throw new RuntimeException("Invoice not found with UUID: " + uid);
        }
        invoiceRepository.deleteById(uid);
    }
}
