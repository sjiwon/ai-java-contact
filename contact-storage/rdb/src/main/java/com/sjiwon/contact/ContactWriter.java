package com.sjiwon.contact;

import com.sjiwon.contact.domain.Contact;

public interface ContactWriter {
    Contact create(final Contact target);

    void delete(final Contact target);
}
