package com.brzesky.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brzesky.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>
{
	@Transactional(readOnly = true)//readOnly diz que a operação não precisa ser envolvida numa consuta a banco, faz ficar mais rápida e não dá lock em recursos de banco.
	Cliente findByEmail(String email);
}
