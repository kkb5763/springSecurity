import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    @Column(name = "vm_host")
    private String vmHost;

    @Column(name = "server_type")
    private String serverType; // IaaS, SaaS, PaaS

    private String os;

    private String manager;

    @Column(name = "installed_software", columnDefinition = "TEXT")
    private String installedSoftware;
}
