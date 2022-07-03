

import java.util.List;
import java.util.stream.Collectors;

import com.example.UberApi.modelo.Veiculo;

public class VeiculoDto {
	private Long id;
	private String placa ;
	private String modelo;
	private String cor ;
	private Integer ano ;
	
	
	
	public VeiculoDto() {}
	
	
	public VeiculoDto(Veiculo veiculo) {
		this.id=veiculo.getId();
		this.placa=veiculo.getPlaca();
		this.modelo=veiculo.getModelo();
		this.cor=veiculo.getCor();
		this.ano=veiculo.getAno();
	}
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public static List<VeiculoDto> converter(List<Veiculo> topicos) {
		return topicos.stream().map(VeiculoDto::new).collect(Collectors.toList());
	}
	

}
