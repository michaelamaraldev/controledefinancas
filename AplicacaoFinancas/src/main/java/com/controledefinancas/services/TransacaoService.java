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

        if (tipo.equalsIgnoreCase("despesa")) { // 'type' para 'tipo', 'expense' para 'despesa'
            user.setSaldo(user.getSaldo() - valor); // 'setBalance' para 'setSaldo', 'getBalance' para 'getSaldo', 'amount' para 'valor'
        } else if (tipo.equalsIgnoreCase("receita")) { // 'income' para 'receita'
            user.setSaldo(user.getSaldo() + valor); // 'setBalance' para 'setSaldo', 'getBalance' para 'getSaldo', 'amount' para 'valor'
        }
        userRepository.save(user);

        Transacao transacao = new Transacao();
        transacao.setUser(user);
        transacao.setTipo(tipo); // 'setType' para 'setTipo'
        transacao.setValor(valor); // 'setAmount' para 'setValor'
        transacao.setCategoria(categoria); // 'setCategory' para 'setCategoria'
        transacao.setData(LocalDateTime.now()); // 'setDate' para 'setData'

        return transacaoRepository.save(transacao);
    }

    public List<Transacao> getUserTransactions(Long userId) {
        return transacaoRepository.findByUserId(userId);
    }

    public Map<String, Double> getSummary(Long userId) {
        List<Transacao> transacoes = getUserTransactions(userId); // 'transactions' para 'transacoes'
        double receitaTotal = 0, despesaTotal = 0; // 'income' para 'receitaTotal', 'expense' para 'despesaTotal'

        for (Transacao t : transacoes) {
            if (t.getTipo().equalsIgnoreCase("receita")) receitaTotal += t.getValor(); // 'getType' para 'getTipo', 'income' para 'receita', 'getAmount' para 'getValor'
            else despesaTotal += t.getValor(); // 'expense' para 'despesa', 'getAmount' para 'getValor'
        }

        double saldoAtual = receitaTotal - despesaTotal; // 'balance' para 'saldoAtual'

        Map<String, Double> resumo = new HashMap<>(); // 'summary' para 'resumo'
        resumo.put("receita", receitaTotal); // Chaves do mapa também em português
        resumo.put("despesa", despesaTotal);
        resumo.put("saldo", saldoAtual);

        return resumo;
    }
}