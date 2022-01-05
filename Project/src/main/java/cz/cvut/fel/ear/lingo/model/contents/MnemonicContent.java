package cz.cvut.fel.ear.lingo.model.contents;

import cz.cvut.fel.ear.lingo.model.abstracts.AbstractContent;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class MnemonicContent extends AbstractContent {

    @Basic(optional = false)
    @Column(nullable = false)
    private String mnemonic;

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String content) {
        this.mnemonic = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MnemonicContent that = (MnemonicContent) o;
        return Objects.equals(mnemonic, that.mnemonic);
    }

    @Override
    public int hashCode() {
        return mnemonic != null ? mnemonic.hashCode() : 0;
    }

}