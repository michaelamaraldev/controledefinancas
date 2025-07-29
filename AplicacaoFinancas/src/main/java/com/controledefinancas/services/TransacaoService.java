package com.controledefinancas.services;

import com.controledefinancas.models.Transacao;
import com.controledefinancas.models.User;
import com.controledefinancas.repositories.TransacaoRepository;
import com.controledefinancas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UserRepository userRepository;

    public Transacao addTransacao(Long userId, String tipo, double valor, String categoria) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (tipo.equalsIgnoreCase("despesa")) {
            user.setSaldo(user.getSaldo() - valor);
        } else if (tipo.equalsIgnoreCase("receita")) { 
            user.setSaldo(user.getSaldo() + valor);
        }
        userRepository.save(user);

        Transacao transacao = new Transacao();
        transacao.setUser(user);
        transacao.setTipo(tipo);
        transacao.setValor(valor);
        transacao.setCategoria(categoria);
        transacao.setData(LocalDateTime.now());

        return transacaoRepository.save(transacao);
    }

    public List<Transacao> getUserTransactions(Long userId) {
        return transacaoRepository.findByUserId(userId);
    }

    public Map<String, Double> getSummary(Long userId) {
        List<Transacao> transacoes = getUserTransactions(userId);
        double receitaTotal = 0, despesaTotal = 0;

        for (Transacao t : transacoes) {
            if (t.getTipo().equalsIgnoreCase("receita")) receitaTotal += t.getValor(); 
            else despesaTotal += t.getValor();
        }

        double saldoAtual = receitaTotal - despesaTotal;

        Map<String, Double> resumo = new HashMap<>();
        resumo.put("receita", receitaTotal);
        resumo.put("despesa", despesaTotal);
        resumo.put("saldo", saldoAtual);

        return resumo;
    }
}
