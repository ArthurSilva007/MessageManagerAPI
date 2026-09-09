-- Criação da tabela de Clientes (Seus assinantes)
CREATE TABLE clients (
                         id UUID PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(150) UNIQUE NOT NULL,
                         api_key VARCHAR(255) UNIQUE NOT NULL,
                         webhook_url VARCHAR(255),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação da tabela de Assinaturas (Controle financeiro)
CREATE TABLE subscriptions (
                               id UUID PRIMARY KEY,
                               client_id UUID NOT NULL,
                               plan_name VARCHAR(50) NOT NULL,
                               status VARCHAR(20) NOT NULL, -- ACTIVE, OVERDUE, CANCELED
                               expires_at TIMESTAMP NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT fk_sub_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
);

-- Criação da tabela de Instâncias (Conexões do WhatsApp)
CREATE TABLE instances (
                           id UUID PRIMARY KEY,
                           client_id UUID NOT NULL,
                           instance_name VARCHAR(100) NOT NULL,
                           phone_number VARCHAR(20),
                           connection_status VARCHAR(30) NOT NULL, -- CONNECTED, DISCONNECTED, WAITING_QR
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT fk_inst_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
);

-- Criação da tabela de Logs de Mensagem (Histórico de disparos)
CREATE TABLE message_logs (
                              id UUID PRIMARY KEY,
                              client_id UUID NOT NULL,
                              instance_id UUID NOT NULL,
                              remote_jid VARCHAR(50) NOT NULL, -- Número de destino
                              message_type VARCHAR(20) NOT NULL, -- TEXT, IMAGE, AUDIO
                              content TEXT,
                              media_url VARCHAR(255),
                              status VARCHAR(20) NOT NULL, -- PENDING, SENT, DELIVERED, READ, FAILED
                              error_message TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_msg_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
                              CONSTRAINT fk_msg_instance FOREIGN KEY (instance_id) REFERENCES instances (id) ON DELETE CASCADE
);

-- Índices para otimizar as buscas no PostgreSQL
CREATE INDEX idx_clients_api_key ON clients (api_key);
CREATE INDEX idx_instances_client_id ON instances (client_id);
CREATE INDEX idx_message_logs_client_id ON message_logs (client_id);
CREATE INDEX idx_message_logs_status ON message_logs (status);