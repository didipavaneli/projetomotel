package br.com.pavaneli.motel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pavaneli.motel.dto.ItemPedidoDto;
import br.com.pavaneli.motel.entity.Hospedagem;
import br.com.pavaneli.motel.entity.ItemPedido;
import br.com.pavaneli.motel.repository.HospedagemRepository;
import br.com.pavaneli.motel.repository.ItemPedidoRepository;
import jakarta.transaction.Transactional;

@Service
public class ItemPedidoService {
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
    private HospedagemRepository hospedagemRepository;
	
	public List<ItemPedidoDto> findall(){
		List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
		return itemPedidos.stream().map(ItemPedidoDto::new).toList();
	}
	
	public void insert(ItemPedidoDto itemPedidoDto){
        ItemPedido itemPedido = new ItemPedido(itemPedidoDto);
        itemPedidoRepository.save(itemPedido);
    }
	public ItemPedidoDto update(ItemPedidoDto itemPedidoDto){
        ItemPedido itemPedido = new ItemPedido(itemPedidoDto);
        return new ItemPedidoDto(itemPedidoRepository.save(itemPedido));
    }
	public void delete(Long id){
		ItemPedido itemPedido = itemPedidoRepository.findById(id).get();
        itemPedidoRepository.delete(itemPedido);
    }
	public ItemPedidoDto findById(Long id) {
        return new ItemPedidoDto(itemPedidoRepository.findById(id).get());
	}
	 @Transactional
	    public void deletarEDesvincularPedido(Long id) {
	        ItemPedido itemPedido = itemPedidoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

	        Hospedagem hospedagem = itemPedido.getHospedagem();
	        if (hospedagem != null) {
	            hospedagem.getItensPedidos().remove(itemPedido);
	            hospedagemRepository.save(hospedagem);
	        }

	        itemPedidoRepository.delete(itemPedido);
	    }

}



	

