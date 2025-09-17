package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;
import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import jakarta.persistence.OneToMany;
import java.util.List;



@Data
@Entity
public class UbicacionBodegaEntity extends BaseEntity{
    private Integer numeroEstante;
    private String canasta;
    private Integer pesoMaxCanasta;
    @PodamExclude
    @OneToMany(mappedBy = "ubicacion")
    private List<MercanciaEntity> itemsMercancia = new ArrayList<>();
    
}
