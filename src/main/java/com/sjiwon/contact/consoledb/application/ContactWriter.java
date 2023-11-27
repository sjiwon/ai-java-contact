package com.sjiwon.contact.consoledb.application;

import com.sjiwon.contact.domain.Contact;

public interface ContactWriter {
    Contact create(final Contact target);

    void delete(final Contact target);
}
